import csv
import pandas as pd
import numpy as np



dict_from_csv = pd.read_csv('/home/dipp/Github/Playground/Data/texts_english.csv', header=None, index_col=0, squeeze=True, sep='}', lineterminator='\n').to_dict()
#print(dict_from_csv)

df = pd.DataFrame.from_dict(dict_from_csv, orient ='index')
#df.columns = ["ID"]
#print(df.keys())
print(df)

# Getting shape of the df
shape = df.shape
  
# Printing Number of columns
print('Number of columns :', shape[1])

"""
new_list = []
for i in range(len(df)):
    l = len((df["ID"][i]))
    new_list.append(l)
#print(new_list)
"""


##New Dataframe that has e new column that counts the number of elements in mention
#df.insert(loc=len(df.columns), column='num', value=new_list)

##Sorting the new Dataframe
#new_df = df.sort_values(by=['num'], ascending = False)


#print(new_df)





