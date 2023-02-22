### DID NOT WORK *******

import pandas as pd

print("############################# START #########################################")
print("############################# CREATING DATAFRAME ################################################")

df = pd.read_json("/global/D1/projects/umod/dipp/playground/Backup/usersAllDipp.json", lines=True, encoding='utf-8')
#df = pd.read_json("/home/dipp/Github/Master-Thesis-dipp/USERID/usersAllDipp copy.json", lines=True)

print("############################# DATAFRAME DONE #####################################################")

cols = [0,1,2,7,10]
df = df[df.columns[cols]]

print("################################ SELECTED THE COLUMNS ######################################################")

df.to_csv('/global/D1/projects/umod/dipp/playground/Result_User/userAll.csv', index = False)
#df.to_csv('/home/dipp/Github/Master-Thesis-dipp/USERID/userAll.csv', index = False)
print("################################# OUTPUT PUBLISHED #####################################################")
print("################################## DONE #############################################################")