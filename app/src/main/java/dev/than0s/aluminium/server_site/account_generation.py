import string
import firebase_admin
from firebase_admin import firestore
from firebase_admin import credentials
from firebase_admin import auth
import secrets
from google.cloud.firestore_v1.base_query import FieldFilter
import constant


cred = credentials.Certificate(constant.ADMIN_SDK_PATH)
app = firebase_admin.initialize_app(cred)
db = firestore.client()

def generate_password(length=12):
    # Define the characters to use for the password
    alphabet = string.ascii_letters + string.digits + string.punctuation
    # Generate a random password
    password = ''.join(secrets.choice(alphabet) for _ in range(length))
    return password

def create_account(email:string,password:string):
    user = auth.create_user(
        email = email,
        password = password
    )
    return user.uid

def create_college_info_document(doc_id:string,doc:dict):
    db.collection(constant.COLLEGE_INFO).document(document_id = doc_id).set(doc)

def modifie_request_status(doc_id:string,doc:dict):
    db.collection(constant.REGISTRATION_REQUESTS).document(document_id = doc_id).set(doc)

def fetch_accepted_request():
    accepted = db.collection(constant.REGISTRATION_REQUESTS).where(filter = FieldFilter(constant.STATUS_APPROVAL_STATUS,"==",True))
    return accepted.stream()
        

if __name__=="__main__":
    # feaching accepted request by admin
    accepted_docs = fetch_accepted_request()

    for doc in accepted_docs:
        dict = doc.to_dict()
        try:
            # account creation
            user_uid = create_account(dict[constant.EMAIL],generate_password())

            #create college_info doc
            create_college_info_document(user_uid,{
                constant.USER_ROLE:dict[constant.USER_ROLE]
            })

            # modify the account generation status as True
            dict[constant.STATUS][constant.ACCOUNT_GENERATED_STATUS] = True

            # make update in firestore
            modifie_request_status(doc.id,dict)
            
            print("account generated successfully")

        except Exception as e:
            print(f"Error: {e}")
    
    print("script executed successfully")

# rejected = db.collection(registrationRequests).where(filter = FieldFilter(approvalStatus,"==",False))
# rejected_docs = rejected.stream()