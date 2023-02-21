import sys

f=open(str(sys.argv[1]), "r")
txtlines = f.readlines()
f.close()

f=open(str(sys.argv[2]), "r")
placelines = f.readlines()
f.close()


if len(txtlines)!=len(placelines):
    quit()

countries = []
places = []
counts = {}
placedic = {}
#build dictionary
for i in range(len(txtlines)):
    line=txtlines[i].rstrip()
    placedic[line] = placelines[i].rstrip()


f=open(str(sys.argv[3]), "r")
analyzelines = f.readlines()
f.close()



for i in range(len(analyzelines)):
    word=analyzelines[i].rstrip()
    if word in placedic:
        print(placedic[word])
    else:
        print(['none', 'none', 'none', 'none', 'none']