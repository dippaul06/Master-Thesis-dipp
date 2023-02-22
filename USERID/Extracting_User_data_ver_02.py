import pandas as pd
import json
from json import JSONDecodeError

print("############################# START #########################################")
data = []
cols = ['createdAt', 'followers', 'favourites', 'location', 'userId']
file_name = "/global/D1/projects/umod/dipp/playground/Backup/usersAllDipp.json"
counts = 0

print("############################# OPENING FILE ################################################")

with open(file_name, encoding='utf-8') as f:
    for line in f:
        try : 
            doc = json.loads(line)
            lst = [doc['createdAt'], doc['followers'], doc['favourites'], doc['location'], doc['userId']]
            data.append(lst)
        except (JSONDecodeError, json.JSONDecodeError):
            counts+=1
            continue

print("############################# LIST DONE #####################################################")
print("Skipped: " ,  counts)

print("############################# CREATING DATAFRAME ################################################")
df = pd.DataFrame(data=data, columns=cols)
print("############################# DATAFRAME DONE #####################################################")

df.to_csv('/global/D1/projects/umod/dipp/playground/Result_User/userAll.csv', index = False)
#df.to_csv('/home/dipp/Github/Master-Thesis-dipp/USERID/userAll.csv', index = False)
print("################################# OUTPUT PUBLISHED #####################################################")
print("################################## DONE #############################################################")