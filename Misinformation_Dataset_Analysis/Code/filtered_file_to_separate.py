import csv

data_file = '/global/D1/projects/umod/dipp/Misinformation_Dataset/after_first_filter.csv'

cat_01_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_01_only_3.csv'
cat_02_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_02_only_3.csv'
cat_03_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_03_only_3.csv'
cat_04_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_04_only_3.csv'
cat_05_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_05_only_3.csv'
cat_06_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_06_only_3.csv'
cat_07_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_07_only_3.csv'
cat_08_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_08_only_3.csv'
cat_09_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_09_only_3.csv'
cat_all_only_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_all_only_3.csv'


cat_01_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_01_only_2_or_3.csv'
cat_02_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_02_only_2_or_3.csv'
cat_03_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_03_only_2_or_3.csv'
cat_04_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_04_only_2_or_3.csv'
cat_05_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_05_only_2_or_3.csv'
cat_06_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_06_only_2_or_3.csv'
cat_07_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_07_only_2_or_3.csv'
cat_08_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_08_only_2_or_3.csv'
cat_09_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_09_only_2_or_3.csv'
cat_all_only_2_or_3 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_all_only_2_or_3.csv'


def decode_func(givenencode):
    decode_array = []
    for i in range(9):
        temp = ((givenencode >> (i * 2)) & 3)
        decode_array.append(temp)

    return decode_array


file1 = open(cat_01_only_3, "a")  # append mode
file1.write('i,j,contacts'+'\n')
file1.close()

file2 = open(cat_02_only_3, "a")  # append mode
file2.write('i,j,contacts'+'\n')
file2.close()

file3 = open(cat_03_only_3, "a")  # append mode
file3.write('i,j,contacts'+'\n')
file3.close()

file4 = open(cat_04_only_3, "a")  # append mode
file4.write('i,j,contacts'+'\n')
file4.close()

file5 = open(cat_05_only_3, "a")  # append mode
file5.write('i,j,contacts'+'\n')
file5.close()

file6 = open(cat_06_only_3, "a")  # append mode
file6.write('i,j,contacts'+'\n')
file6.close()

file7 = open(cat_07_only_3, "a")  # append mode
file7.write('i,j,contacts'+'\n')
file7.close()

file8 = open(cat_08_only_3, "a")  # append mode
file8.write('i,j,contacts'+'\n')
file8.close()

file9 = open(cat_09_only_3, "a")  # append mode
file9.write('i,j,contacts'+'\n')
file9.close()

file10 = open(cat_all_only_3, "a")  # append mode
file10.write('i,j,contacts'+'\n')
file10.close()



file11 = open(cat_01_only_2_or_3, "a")  # append mode
file11.write('i,j,contacts'+'\n')
file11.close()

file12 = open(cat_02_only_2_or_3, "a")  # append mode
file12.write('i,j,contacts'+'\n')
file12.close()

file13 = open(cat_03_only_2_or_3, "a")  # append mode
file13.write('i,j,contacts'+'\n')
file13.close()

file14 = open(cat_04_only_2_or_3, "a")  # append mode
file14.write('i,j,contacts'+'\n')
file14.close()

file15 = open(cat_05_only_2_or_3, "a")  # append mode
file15.write('i,j,contacts'+'\n')
file15.close()

file16 = open(cat_06_only_2_or_3, "a")  # append mode
file16.write('i,j,contacts'+'\n')
file16.close()

file17 = open(cat_07_only_2_or_3, "a")  # append mode
file17.write('i,j,contacts'+'\n')
file17.close()

file18 = open(cat_08_only_2_or_3, "a")  # append mode
file18.write('i,j,contacts'+'\n')
file18.close()

file19 = open(cat_09_only_2_or_3, "a")  # append mode
file19.write('i,j,contacts'+'\n')
file19.close()

file20 = open(cat_all_only_2_or_3, "a")  # append mode
file20.write('i,j,contacts'+'\n')
file20.close()


with open(data_file, newline='', encoding='utf-8') as csv_data:
    base_data = csv.DictReader(csv_data, delimiter=',', restkey='types')
    
    for line in base_data:
        # print(type(line['types']))
        cat1 = 0
        cat2 = 0
        cat3 = 0
        cat4 = 0
        cat5 = 0
        cat6 = 0
        cat7 = 0
        cat8 = 0
        cat9 = 0
        cat10 = 0

        cat11 = 0
        cat12 = 0
        cat13 = 0
        cat14 = 0
        cat15 = 0
        cat16 = 0
        cat17 = 0
        cat18 = 0
        cat19 = 0
        cat20 = 0

        temp = line['types']
        if(type(temp) is not str):  # Multiple Values
            for x in temp:
                x = x.replace("]","")
                x = x.replace("[","")
                x = int(x)
                temp_value = decode_func(x)
        
                if(temp_value[0] == 3):
                    cat1 +=1 
                if(temp_value[1] == 3):
                    cat2 +=1
                if(temp_value[2] == 3):
                    cat3 +=1
                if(temp_value[3] == 3):
                    cat4 +=1
                if(temp_value[4] == 3):
                    cat5 +=1
                if(temp_value[5] == 3):
                    cat6 +=1
                if(temp_value[6] == 3):
                    cat7 +=1
                if(temp_value[7] == 3):
                    cat8 +=1
                if(temp_value[8] == 3):
                    cat9 +=1
                if(temp_value[0] == 3 or temp_value[1] == 3 or
                   temp_value[2] == 3 or temp_value[3] == 3 or
                   temp_value[4] == 3 or temp_value[5] == 3 or
                   temp_value[6] == 3 or temp_value[7] == 3 or
                   temp_value[8] == 3):
                    cat10 +=1

                if(temp_value[0] == 3 or temp_value[0] == 2):
                    cat11 +=1 
                if(temp_value[1] == 3 or temp_value[1] == 2):
                    cat12 +=1
                if(temp_value[2] == 3 or temp_value[2] == 2):
                    cat13 +=1
                if(temp_value[3] == 3 or temp_value[3] == 2):
                    cat14 +=1
                if(temp_value[4] == 3 or temp_value[4] == 2):
                    cat15 +=1
                if(temp_value[5] == 3 or temp_value[5] == 2):
                    cat16 +=1
                if(temp_value[6] == 3 or temp_value[6] == 2):
                    cat17 +=1
                if(temp_value[7] == 3 or temp_value[7] == 2):
                    cat18 +=1
                if(temp_value[8] == 3 or temp_value[8] == 2):
                    cat19 +=1
                if(temp_value[0] == 3 or temp_value[1] == 3 or
                   temp_value[2] == 3 or temp_value[3] == 3 or
                   temp_value[4] == 3 or temp_value[5] == 3 or
                   temp_value[6] == 3 or temp_value[7] == 3 or
                   temp_value[8] == 3 or temp_value[0] == 2 or temp_value[1] == 2 or
                   temp_value[2] == 2 or temp_value[3] == 2 or
                   temp_value[4] == 2 or temp_value[5] == 2 or
                   temp_value[6] == 2 or temp_value[7] == 2 or
                   temp_value[8] == 2
                   ):
                    cat20 +=1
 
        if(type(temp) is str):  # One Value
            temp = temp.replace("[", "")
            temp = temp.replace("]", "")
            temp = int(temp)
            temp_value = decode_func(temp)

            if(temp_value[0] == 3):
                    cat1 +=1 
            if(temp_value[1] == 3):
                    cat2 +=1
            if(temp_value[2] == 3):
                    cat3 +=1
            if(temp_value[3] == 3):
                    cat4 +=1
            if(temp_value[4] == 3):
                    cat5 +=1
            if(temp_value[5] == 3):
                    cat6 +=1
            if(temp_value[6] == 3):
                    cat7 +=1
            if(temp_value[7] == 3):
                    cat8 +=1
            if(temp_value[8] == 3):
                    cat9 +=1
            if(temp_value[0] == 3 or temp_value[1] == 3 or
                   temp_value[2] == 3 or temp_value[3] == 3 or
                   temp_value[4] == 3 or temp_value[5] == 3 or
                   temp_value[6] == 3 or temp_value[7] == 3 or
                   temp_value[8] == 3):
                    cat10 +=1
            if(temp_value[0] == 3 or temp_value[0] == 2):
                    cat11 +=1 
            if(temp_value[1] == 3 or temp_value[1] == 2):
                    cat12 +=1
            if(temp_value[2] == 3 or temp_value[2] == 2):
                    cat13 +=1
            if(temp_value[3] == 3 or temp_value[3] == 2):
                    cat14 +=1
            if(temp_value[4] == 3 or temp_value[4] == 2):
                    cat15 +=1
            if(temp_value[5] == 3 or temp_value[5] == 2):
                    cat16 +=1
            if(temp_value[6] == 3 or temp_value[6] == 2):
                    cat17 +=1
            if(temp_value[7] == 3 or temp_value[7] == 2):
                    cat18 +=1
            if(temp_value[8] == 3 or temp_value[8] == 2):
                    cat19 +=1
            if(temp_value[0] == 3 or temp_value[1] == 3 or
                   temp_value[2] == 3 or temp_value[3] == 3 or
                   temp_value[4] == 3 or temp_value[5] == 3 or
                   temp_value[6] == 3 or temp_value[7] == 3 or
                   temp_value[8] == 3 or temp_value[0] == 2 or temp_value[1] == 2 or
                   temp_value[2] == 2 or temp_value[3] == 2 or
                   temp_value[4] == 2 or temp_value[5] == 2 or
                   temp_value[6] == 2 or temp_value[7] == 2 or
                   temp_value[8] == 2
                   ):
                    cat20 +=1


        if(cat1 != 0):
              file1 = open(cat_01_only_3, "a")  # append mode
              file1.writelines(line['i'] + ',' + line['j'] + ',' + str(cat1) + '\n')
              file1.close()
        if(cat2 != 0):
              file2 = open(cat_02_only_3, "a")  # append mode
              file2.writelines(line['i'] + ',' + line['j'] + ',' + str(cat2) + '\n')
              file2.close()
        if(cat3 != 0):
              file3 = open(cat_03_only_3, "a")  # append mode
              file3.writelines(line['i'] + ',' + line['j'] + ',' + str(cat3) + '\n')
              file3.close()
        if(cat4 != 0):
              file4 = open(cat_04_only_3, "a")  # append mode
              file4.writelines(line['i'] + ',' + line['j'] + ',' + str(cat4) + '\n')
              file4.close()
        if(cat5 != 0):
              file5 = open(cat_05_only_3, "a")  # append mode
              file5.writelines(line['i'] + ',' + line['j'] + ',' + str(cat5) + '\n')
              file5.close()
        if(cat6 != 0):
              file6 = open(cat_06_only_3, "a")  # append mode
              file6.writelines(line['i'] + ',' + line['j'] + ',' + str(cat6) + '\n')
              file6.close()
        if(cat7 != 0):
              file7 = open(cat_07_only_3, "a")  # append mode
              file7.writelines(line['i'] + ',' + line['j'] + ',' + str(cat7) + '\n')
              file7.close()
        if(cat8 != 0):
              file8 = open(cat_08_only_3, "a")  # append mode
              file8.writelines(line['i'] + ',' + line['j'] + ',' + str(cat8) + '\n')
              file8.close()
        if(cat9 != 0):
              file9 = open(cat_09_only_3, "a")  # append mode
              file9.writelines(line['i'] + ',' + line['j'] + ',' + str(cat9) + '\n')
              file9.close()
        if(cat10 != 0):
              file10 = open(cat_all_only_3, "a")  # append mode
              file10.writelines(line['i'] + ',' + line['j'] + ',' + str(cat10) + '\n')
              file10.close()

        if(cat11 != 0):
              file11 = open(cat_01_only_2_or_3, "a")  # append mode
              file11.writelines(line['i'] + ',' + line['j'] + ',' + str(cat11) + '\n')
              file11.close()
        if(cat12 != 0):
              file12 = open(cat_02_only_2_or_3, "a")  # append mode
              file12.writelines(line['i'] + ',' + line['j'] + ',' + str(cat12) + '\n')
              file12.close()
        if(cat13 != 0):
              file13 = open(cat_03_only_2_or_3, "a")  # append mode
              file13.writelines(line['i'] + ',' + line['j'] + ',' + str(cat13) + '\n')
              file13.close()
        if(cat14 != 0):
              file14 = open(cat_04_only_2_or_3, "a")  # append mode
              file14.writelines(line['i'] + ',' + line['j'] + ',' + str(cat14) + '\n')
              file14.close()
        if(cat15 != 0):
              file15 = open(cat_05_only_2_or_3, "a")  # append mode
              file15.writelines(line['i'] + ',' + line['j'] + ',' + str(cat15) + '\n')
              file15.close()
        if(cat16 != 0):
              file16 = open(cat_06_only_2_or_3, "a")  # append mode
              file16.writelines(line['i'] + ',' + line['j'] + ',' + str(cat16) + '\n')
              file16.close()
        if(cat17 != 0):
              file17 = open(cat_07_only_2_or_3, "a")  # append mode
              file17.writelines(line['i'] + ',' + line['j'] + ',' + str(cat17) + '\n')
              file17.close()
        if(cat18 != 0):
              file18 = open(cat_08_only_2_or_3, "a")  # append mode
              file18.writelines(line['i'] + ',' + line['j'] + ',' + str(cat18) + '\n')
              file18.close()
        if(cat19 != 0):
              file19 = open(cat_09_only_2_or_3, "a")  # append mode
              file19.writelines(line['i'] + ',' + line['j'] + ',' + str(cat19) + '\n')
              file19.close()
        if(cat20 != 0):
              file20 = open(cat_all_only_2_or_3, "a")  # append mode
              file20.writelines(line['i'] + ',' + line['j'] + ',' + str(cat20) + '\n')
              file20.close()
        
        
    