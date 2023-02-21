import csv
import pandas as pd
import matplotlib.pyplot as plot


with open("/home/dipp/Github/Playground/Data/texts.csv") as csv_file:
    ##Getting rid of blank lines
    #lines = (line.rstrip() for line in csv_file) 
    #lines = list(line for line in lines if line)

    ##Creating our List
    #readCSV = csv.reader(lines, delimiter=',')
    readCSV = csv.reader(csv_file, delimiter=',')
    list_test = list(readCSV)
    
    ##Separating the List
    id_list = [i[0] for i in list_test]
    text_list = [i[1:] for i in list_test]

    ##Create an intermediate dictionary
    intermediate_dictionary = {'ID':id_list, 'TEXT':text_list}

    ##Take the intermediate dictionary to dataframe
    df = pd.DataFrame(intermediate_dictionary)
    

    print(df)
    print(id_list)
    for i in range(len(id_list)):
        print(id_list[i])



    """
    new_list = []
    for i in range(len(df)):
        l = len((df["ID"][i]))
        new_list.append(l)
    print(new_list)

    ##New Dataframe that has e new column that counts the number of elements in mention
    df.insert(loc=len(df.columns), column='num', value=new_list)

    ##Sorting the new Dataframe
    new_df = df.sort_values(by=['num'], ascending = False)


    print(new_df)
    """
    
    
    
