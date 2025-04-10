import firebase_admin
from firebase_admin import firestore, credentials, messaging
from google.cloud.firestore_v1.base_query import FieldFilter
import constant

# Initialize Firebase
cred = credentials.Certificate(constant.ADMIN_SDK_PATH)
firebase_admin.initialize_app(cred)
db = firestore.client()

# Fetch all pending notifications
def get_pending_notifications():
    return db.collection(constant.PUSH_NOTIFICATION).where(
        filter=FieldFilter(constant.PUSH_STATUS, "==", False)
    ).stream()

# Get FCM token for a given user
def get_fcm_token(user_id: str) -> str | None:
    doc_ref = db.collection("users").document(user_id)
    doc = doc_ref.get()
    if doc.exists:
        data = doc.to_dict()
        return data.get("cloud_messaging_token")
    return None

# Append notification ID to user’s "notifications" array
def push_in_user_collection(userId: str, notiId: str):
    try:
        db.collection("users").document(userId).update({
            "notifications": firestore.ArrayUnion([notiId])
        })
    except Exception as e:
        print(f"Error updating notifications for user {userId}: {e}")

# Push a notification to multiple users
def push_notification(title: str, body: str, docs: list, notiId: str):
    for doc in docs:
        user_id = doc.id
        print(f"userId: {user_id}")
        token = get_fcm_token(user_id)
        if not token:
            print(f"No FCM token for user: {user_id}")
            continue
        for t in token:
            message = messaging.Message(
                notification=messaging.Notification(title=title, body=body),
                token=t,
            )
            try:
                response = messaging.send(message)
                push_in_user_collection(userId=user_id, notiId=notiId)
                print(f'Successfully sent message to {user_id}:', response)
            except Exception as e:
                print(f"Failed to send notification to {user_id}: {e}")

# Build dynamic Firestore query for a role
def build_query(role: str, course_list: list = None, batch: dict = None):
    query = db.collection("college_info").where(
        filter=FieldFilter("role", "==", role)
    )
    if course_list:
        query = query.where(filter=FieldFilter("course", "in", course_list))
    if batch:
        query = query.where(filter=FieldFilter("from", ">=", batch.get("from"))).where(filter=FieldFilter("to", "<=", batch.get("to")))
    return query

# Filter users based on roles and send notifications
def process_filter(role: str, filter_data: dict, title: str, content: str, notiId: str):
    if not filter_data:
        return
    if(role == "Staff"):
        if(filter_data is True):
            docs = db.collection("college_info").where(
                filter = FieldFilter("role","==",role)
            ).stream()
            push_notification(title,content,docs,notiId)
    else:
        course_list = []
        if filter_data.get("mba"):
            course_list.append("MBA")
        if filter_data.get("mca"):
            course_list.append("MCA")
        batch = filter_data.get("batch")
        query = build_query(role, course_list, batch)
        docs = list(query.stream())
        push_notification(title, content, docs, notiId)

# Main execution
def main():
    pending_notifications = get_pending_notifications()
    for doc in pending_notifications:
        try:
            data = doc.to_dict()
            notiId = doc.id
            title = data["content"]["title"]
            content = data["content"]["content"]
            filters = data["filters"]

            process_filter("Alumni", filters.get("alumni"), title, content, notiId)
            process_filter("Student", filters.get("student"), title, content, notiId)
            process_filter("Staff", filters.get("staff"), title, content, notiId)

            # Mark notification as sent
            doc.reference.update({constant.PUSH_STATUS: True})
        except Exception as e:
            print(f"Error processing notification: {e}")

    print("✅ All pending notifications processed.")

if __name__ == "__main__":
    main()
