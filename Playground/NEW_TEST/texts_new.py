import re
import pandas as pd

temp_df = pd.DataFrame()
count = 0

#file_folder = "/home/dipp/Github/Playground/Data/texts_english.csv"
file_folder = "/global/D1/projects/umod/dipp/playground/texts_english.csv"
pat = '^\{([0-9]*[,]?[ ]?)*\}'
test_list = []

print("First")
#df = pd.read_csv(file_folder, sep='},' , names = ['TEXT'],  engine='python')

temp_df = pd.DataFrame()
count = 0
df = pd.read_csv(file_folder, sep=pat , names = ['TEXT'],  engine='python', chunksize=100000)

for chunk in df:
    #print(chunk)
    count += 100000
    print(count)
    temp_df = temp_df.append(chunk)


print('1')
text_column = temp_df.iloc[:,-1:]
print("First DF")
text_list = text_column.values.tolist()
#print(len(text_list))

file1 = open(file_folder, 'r')
count = 0

for line in file1:
    test = re.search(pat, line)
    if( test!= 'None'):
        test_list.append(test.group())
    else:
        count += 1
    #test_list.append(test.group())
        
print('Done Here')

print(count)

file1.close()
print("File Done")

num_list = []
for i in test_list:
    l = len(i)
    num_list.append(l/21)
#print(num_list)

print("Straing next DF")

new_df = pd.DataFrame()

new_df['NUM'] = num_list
new_df['TEXT'] = text_list



#print(new_df)
print("DF Done")

##Sorting the new Dataframe
new_df_01 = new_df.sort_values(by=['NUM'], ascending = False)
#print(new_df_01)

## Specify the number of elements to plot and create a new dataframe
for_plot_df = new_df_01.head(100)

for_plot_df.to_csv('/global/D1/projects/umod/dipp/playground/result_csv/texts.csv', index = False)







