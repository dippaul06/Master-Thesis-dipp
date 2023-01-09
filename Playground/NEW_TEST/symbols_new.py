from itertools import count
import pandas as pd


##Converting the json file to a pandas dataframe
df = pd.read_json("/global/D1/projects/umod/dipp/playground/symbols.json", lines=True)



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

res_df = for_plot_df[['text', 'num']]

#res_df.to_csv('/home/dipp/Github/Playground/DEMO_RES/symbols.csv', index = False)

res_df.to_csv('/global/D1/projects/umod/dipp/playground/result_csv/symbols.csv', index = False)