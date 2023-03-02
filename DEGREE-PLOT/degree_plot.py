import pandas as pd
import matplotlib.pyplot as plt


print('**********************************START********************************************************')
#file_path_all = '/global/D1/projects/umod/dipp/playground/result_graph/degree_all.csv'
#file_path_in = '/global/D1/projects/umod/dipp/playground/result_graph/degree_in.csv'
file_path_out = '/global/D1/projects/umod/dipp/playground/result_graph/degree_out.csv'

#ds = pd.read_csv(file_path_all)
#ds = pd.read_csv(file_path_in)
ds = pd.read_csv(file_path_out)
print('**********************************DATAFRAME DONE********************************************************')

plt.hist(ds.iloc[:, 1], bins=45)
plt.yscale('log')
plt.xlabel('Degree')
plt.ylabel('Count')
print('**********************************HIST MADE********************************************************')

#plt.savefig('/home/dipp/Github/Master-Thesis-dipp/RES/degree_all.pdf', format='pdf', papertype='letter')
#plt.savefig('/home/dipp/Github/Master-Thesis-dipp/RES/degree_in.pdf', format='pdf', papertype='letter')
plt.savefig('/home/dipp/Github/Master-Thesis-dipp/RES/degree_out.pdf',format='pdf', papertype='letter')
print('**********************************HIST SAVED********************************************************')

print('**********************************DONE********************************************************')