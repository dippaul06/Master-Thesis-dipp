print("Starting")
file_folder = "/global/D1/projects/umod/dipp/playground/texts_english.csv"
num = sum(1 for line in open(file_folder))
print("Total Lines:" , num)
print("Total Lines/2:" , num/2)