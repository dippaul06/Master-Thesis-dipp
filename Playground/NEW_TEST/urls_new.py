import csv
import pandas as pd


with open("/global/D1/projects/umod/dipp/playground/urls.csv") as csv_file:
    ##Getting rid of blank lines
    lines = (line.rstrip() for line in csv_file) 
    lines = list(line for line in lines if line)


    ##Creating our List
    readCSV = csv.reader(lines, delimiter=',')
    list_test = list(readCSV)
    
    ##Separating the List    
    url_list = []
    url_list = [e[-1] for e in list_test]
    #print(url_list)

    id_list = []
    id_list = [e[:-1] for e in list_test]
    #print(id_list)

    ##Create an intermediate dictionary
    intermediate_dictionary = {'ID':id_list, 'URL':url_list}

    ##Take the intermediate dictionary to dataframe
    df = pd.DataFrame(intermediate_dictionary)
    
    
    new_list = []
    for i in range(len(df)):
        l = len((df["ID"][i]))
        new_list.append(l)
    #print(new_list)

    ##New Dataframe that has e new column that counts the number of elements in mention
    df.insert(loc=len(df.columns), column='num', value=new_list)

    ##Sorting the new Dataframe
    new_df = df.sort_values(by=['num'], ascending = False)

    ## Specify the number of elements to plot and create a new dataframe
    for_plot_df = new_df.head(100)

    res_df = for_plot_df[['URL', 'num']]
    res_df.to_csv('/global/D1/projects/umod/dipp/playground/result_csv/urls.csv', index = False)
    #print(res_df)