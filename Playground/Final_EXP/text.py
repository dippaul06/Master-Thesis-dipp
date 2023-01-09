import re
import pandas as pd



file_folder = "/home/dipp/Github/Playground/Data/texts_english.csv"
pat = '^\{([0-9]*[,]?[ ]?)*\}'
#keys = ["NUM", "TEXT"]
#int_dict = dict.fromkeys(keys)
#id_list = []

test_list = []


df = pd.read_csv(file_folder, sep='},' , names = ['TEXT'], engine='python')
text_column = df.iloc[:,-1:]
#print(text_column)
text_list = text_column.values.tolist()
#print(len(text_list))

file1 = open(file_folder, 'r')

for line in file1:
    test = re.search(pat, line)
    if( test!= 'None'):
        test_list.append(test.group())
    


file1.close()

num_list = []
for i in test_list:
    l = len(i)
    num_list.append(l/21)
print(num_list)


#print(len(test_list))
new_df = pd.DataFrame()
#new_df['ID'] = test_list
new_df['NUM'] = num_list
new_df['TEXT'] = text_list

"""
new_list = []
for i in range(len(df)):
    l = len((new_df["ID"][i]))
    new_list.append(l/21)
"""




#new_df.insert(loc=len(new_df.columns), column='NUM', value=new_list)

print(new_df)

##Sorting the new Dataframe
new_df_01 = new_df.sort_values(by=['NUM'], ascending = False)

## Specify the number of elements to plot and create a new dataframe
for_plot_df = new_df_01.head(100)

for_plot_df.to_csv('/home/dipp/Github/Playground/DEMO_RES/texts.csv', index = False)





