import ast
import pandas as pd
from ast import literal_eval

import traceback

#df = pd.read_json (r'/home/dipp/Github/Playground/Data/tags.json', lines=True, encoding="utf8")

#df = pd.read_json (r'/global/D1/projects/umod/dipp/playground/tags.json', lines=True, encoding="utf8")

#df = pd.read_json(open("/global/D1/projects/umod/dipp/playground/tags.json", "r", encoding="utf8"),lines=True, chunksize=1)
df = list(pd.read_json(open("/home/dipp/Github/Playground/Data/tags.json", "r", encoding="utf8"),lines=True, chunksize=1))
#print(len(df))

res_dct = map(lambda i: (df[i], df[i+1]), range(len(df)-1)[::2])
print(list(res_dct))

#print(df[0][0])

"""
new_list = []
for i in range(len(df)):
    l = len((df["mentions"][i]))
    new_list.append(l)


##New Dataframe that has e new column that counts the number of elements in mention
df.insert(loc=len(df.columns), column='num', value=new_list)

##Sorting the new Dataframe
new_df = df.sort_values(by=['num'], ascending = False)


## Specify the number of elements to plot and create a new dataframe
for_plot_df = new_df.head(100)

#print(for_plot_df)
res_df = for_plot_df[['text', 'num']]
print(res_df)


#res_df.to_csv('/global/D1/projects/umod/dipp/playground/result_csv/tags.csv', index = False)

"""



