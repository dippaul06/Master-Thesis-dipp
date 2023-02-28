import pandas as pd
import csv

print("############################# START #########################################")
data = []
cols = ['createdAt', 'followers', 'favourites', 'location', 'userId']
file_name_01 = "/home/dipp/Github/Master-Thesis-dipp/DEGREE-PLOT/degree_all.csv"
file_name_01 = "/home/dipp/Github/Master-Thesis-dipp/DEGREE-PLOT/degree_in.csv"
file_name_01 = "/home/dipp/Github/Master-Thesis-dipp/DEGREE-PLOT/degree_out.csv"
counts = 0

print("############################# OPENING FILE ################################################")

with open(file_name_01, encoding='utf-8') as f:
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


print("################################## DONE #############################################################")