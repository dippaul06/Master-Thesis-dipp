import re

##This converts the DateTime for CreatedAT
print("############################# START #########################################")

with open("/global/D1/projects/umod/dipp/playground/Backup/usersAllDipp.json", "r+") as f:
#with open("/home/dipp/Github/Master-Thesis-dipp/USERID/usersAllDipp copy.json", "r+") as f:
    file = f.readlines()
    print("######################## READ THE FILE ###############################################")

    regex = r"\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}"
    i=0
    counts=0
    file_01 = [] 

    for i in range(len(file)):
        date = file[i]
        x = re.search(regex, date)
        #subst = '"{}"'.format(x.group())
        try:
            subst = f'"{x.group()}"'
            result = re.sub(regex, subst, date, 0)
            file_01.append(result)
            #file[i] = result
        except AttributeError:
            #subst = f'"{x}"'
            #result = re.sub(regex, subst, date, 0)
            #file[i] = result
            counts += 1
            continue

        #result = re.sub(regex, subst, date, 0)
        #file[i] = result

    print("############################## DONE THE REGEX WORK ###############################################")   
    print("Skipped: " ,  counts) 
    
    
    #file_str = ''.join(map(str, file))
    file_str = ''.join(map(str, file_01))
    print("############################### STRING CONVERTION DONE #####################################################")
    
    f.seek(0)
    print("################################# STRATING THE WRITE OPERATION ###############################################################")
    f.write(file_str)
    print("################################# WRITE OPERATION DONE ###############################################################")
    f.truncate()
    print("################################## DONE #############################################################")