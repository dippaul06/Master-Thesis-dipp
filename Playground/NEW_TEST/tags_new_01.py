import numpy as np
import pandas as pd
import gc
import linecache
print('I am here begin ')

#from tqdm import tqdm
#import time


df = pd.DataFrame()
count = 0
an_c = 0
num = sum(1 for line in open("/global/D1/projects/umod/dipp/playground/tags.json"))
#num = sum(1 for line in open("/home/dipp/Github/Playground/Data/tags.json"))
print("Total Lines:" , num)

def read_file(p):
    #data = pd.json_normalize(p)
    count = 0
    an_c = 0
    print("Entered here")
    #for i in range(num):
    try:
        #for i in tqdm (range (101), 
        #    desc="Loading…", 
        #    ascii=False, ncols=75):
        #    time.sleep(0.01)
        df = pd.read_json (p, lines=True, encoding='utf8')
            #if(i != 0):
            #    df = pd.read_json ("/global/D1/projects/umod/dipp/playground/tags.json", lines=True, encoding='utf8', nrows=i, skiprows=[0, i-1], chunksize=100000)
            #else:
            #    df = pd.read_json ("/global/D1/projects/umod/dipp/playground/tags.json", lines=True, encoding='utf8', nrows=i, chunksize=100000)
        #print(i)
        an_c += 1
        print(an_c)
    except:
        #for i in tqdm (range (101), 
        #    desc="Loading…", 
        #    ascii=False, ncols=75):
        #    time.sleep(0.01)
        count += 1
        df = pd.read_json (p, lines=True, encoding='utf8', skiprows=[an_c])
            
        an_c += 1
        print(an_c)
    
    print("Total Lines:" , num)
    print('Skipped: ', count)
    return df


df = read_file("/global/D1/projects/umod/dipp/playground/tags.json")
#df = read_file("/home/dipp/Github/Playground/Data/tags.json")



#df = pd.read_json ("/global/D1/projects/umod/dipp/playground/tags.json", lines=True, encoding='utf8', chunksize=10000)


#for chunk in df:
#    #print(chunk)
#    count += 100000
#    print(count)
#    temp_df = temp_df.append(chunk)


#print(df)
print('POS 1')
df_text = df.iloc[:,-1:]
print('POS 2')
df_id = df.iloc[:, :1]
#df_id = df.iloc['text']
#print(df_id)

lst = [df]
del df
del lst
gc.collect()
print('POS 3')

id_list = df_id.values.tolist()
#print(id_list[0])
print('POS 4')

lst1 = [df_id]
del df_id
del lst1
gc.collect()

num_list = []
for i in id_list:
    for j in i:
        num_list.append(len(j))

#print(num_list)
print('POS 5')



df_text.insert(loc=len(df_text.columns), column='num', value=num_list)
#print(df_text)
del num_list
gc.collect()
print('POS 6')

new_df = df_text.sort_values(by=['num'], ascending = False)
#print(new_df)
print('POS 7')
lst2 = [df_text]
del df_text
del lst2
gc.collect()

for_plot_df = new_df.head(100)
print('POS 8')
lst3 = [new_df]
del new_df
del lst3
gc.collect()
#print(for_plot_df)
for_plot_df.to_csv('/global/D1/projects/umod/dipp/playground/result_csv/tags.csv', index = False)