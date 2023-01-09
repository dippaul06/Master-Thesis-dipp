import pandas as pd 
import re
#file_folder = "/home/dipp/Github/Playground/Data/texts.csv"
print("Starting")
file_folder = "/global/D1/projects/umod/dipp/playground/texts_english.csv"
pat = '^\{([0-9]*[,]?[ ]?)*\}'

id_list = []
text_list = []

num = sum(1 for line in open(file_folder))
print("Total Lines:" , num)

file1 = open(file_folder, 'r')
print("Opened File")
count = 0

for line in file1:

    try:
        ids = re.search(pat, line)
        texts = re.sub(pat, ' ', line)
        id_list.append(ids.group())
        text_list.append(texts)
    except AttributeError:
        ids = re.search(pat, line)
        texts = re.sub(pat, ' ', line)
        count += 1

print("Skipped:" ,count)
print("Done with List")

num_list = []
for i in id_list:
    l = len(i)
    num_list.append(l/21)

print("Got the Num List")
new_df = pd.DataFrame()

new_df['NUM'] = num_list
new_df['TEXT'] = text_list

print("Appended the list to DF")

new_df_01 = new_df.sort_values(by=['NUM'], ascending = False)
print("NEW DF Done")
for_plot_df = new_df_01.head(500)
print("For Plot DF done")
print("Starting the Csv")
for_plot_df.to_csv('/global/D1/projects/umod/dipp/playground/result_csv/texts.csv', index = False)
print("Done.....")







