import requests
import string
import firebase_admin
from firebase_admin import firestore
from firebase_admin import credentials
from google.cloud.firestore_v1.base_query import FieldFilter
import constant



cred = credentials.Certificate(constant.ADMIN_SDK_PATH)
app = firebase_admin.initialize_app(cred)
db = firestore.client()


def send_password_reset_email(email, api_key = constant.WEB_API_KEY):
    url = f"https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key={api_key}"
    
    payload = {
        "requestType": "PASSWORD_RESET",
        "email": email,
    }

    headers = {
        "Content-Type": "application/json"
    }

    response = requests.post(url, json=payload, headers=headers)

    if response.status_code == 200:
        print("Password reset email sent successfully.")
    else:
        print(f"Error sending password reset email: {response.json()}")


def modifie_request_status(doc_id:string,doc:dict):
    db.collection(constant.REGISTRATION_REQUESTS).document(document_id = doc_id).set(doc)


def fetch_generated_accounts():
    accepted = db.collection(constant.REGISTRATION_REQUESTS).where(filter = FieldFilter(constant.STATUS_ACCOUNT_GENERATED_STATUS,"==",True)).where(filter = FieldFilter(constant.STATUS_EMAIL_SEND_STATUS,"==",False))
    return accepted.stream()

if __name__=="__main__":
    # feaching accepted request by admin
    generated_docs = fetch_generated_accounts()

    for doc in generated_docs:
        dict = doc.to_dict()
        try:
            # account creation
            send_password_reset_email(dict[constant.EMAIL])

            # modify the account generation status as True
            dict[constant.STATUS][constant.EMAIL_SEND_STATUS] = True

            # make update in firestore
            modifie_request_status(doc.id,dict)
            
            print("operation executed successfully")

        except Exception as e:
            print(f"Error: {e}")