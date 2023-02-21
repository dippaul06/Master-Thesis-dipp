from itertools import count
import pandas as pd
import matplotlib.pyplot as plot

##Converting the json file to a pandas dataframe
df = pd.read_json("/home/dipp/Github/Playground/Data/symbols.json", lines=True)



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

"""
##Bar Plot of first n Elements
for_plot_df.plot.bar(x="text", y="num", rot=1, title="Test", logy=True)

plot.show(block=True)

##Line Graph
for_plot_df.plot(x="text", y="num", kind="line")
plot.show()
"""

