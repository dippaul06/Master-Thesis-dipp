import pandas as pd 
import re
#file_folder = "/home/dipp/Github/Playground/Data/texts.csv"
file_folder = "/global/D1/projects/umod/dipp/playground/texts.csv"
pat = '^\{([0-9]*[,]?[ ]?)*\}'

id_list = []
text_list = []

num = sum(1 for line in open(file_folder))
print("Total Lines:" , num)

file1 = open(file_folder, 'r')
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

print(count)

num_list = []
for i in id_list:
    l = len(i)
    num_list.append(l/21)


new_df = pd.DataFrame()

new_df['NUM'] = num_list
new_df['TEXT'] = text_list

new_df_01 = new_df.sort_values(by=['NUM'], ascending = False)
for_plot_df = new_df_01.head(500)
for_plot_df.to_csv('/global/D1/projects/umod/dipp/playground/result_csv/texts-low.csv', index = False)







