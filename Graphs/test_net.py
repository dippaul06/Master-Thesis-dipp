# /global/D1/projects/umod/dipp/playground/edge_list.csv
# plt.savefig('/home/dipp/Results/Symbols_bar.pdf',bbox_inches='tight', dpi=150)

import pandas as pd
import networkx as nx
#import networkit as nx
import matplotlib.pyplot as plt
from matplotlib.pyplot import figure


print(".........................Starting.......................")
file_folder = "/global/D1/projects/umod/dipp/playground/edge_list.csv"
num = sum(1 for line in open(file_folder))
print("Total Lines:" , num)

df = pd.read_csv("/global/D1/projects/umod/dipp/playground/edge_list.csv")
print("Made the DataFrame")
Gp1 = nx.from_pandas_edgelist(df=df, source='i', target='j', edge_attr='contacts')
print("MADE THE FIRST GRAPH - CONTACTS")
#Gp2 = nx.from_pandas_edgelist(df=df, source='i', target='j', edge_attr='mentions')
#print("MADE THE SECOND GRAPH - MENTIONS")

#vs1 = nx.draw(Gp1)
#vs2 = nx.draw(Gp2)

nodes1 = nx.number_of_nodes(Gp1)
#nodes2 = nx.number_of_nodes(Gp2)

edges1 = nx.number_of_edges(Gp1)
#edges2 = nx.number_of_edges(Gp2)

degree1 = nx.degree(Gp1)
#degree2 = nx.degree(Gp2)


cluster1 = nx.clustering(Gp1)
#cluster2 = nx.clustering(Gp2)





















"""
print("Drawing Graphs")

#nx.write_gpickle(Gp1, "/home/dipp/Github/Graphs/test1.gpickle")
nx.write_gexf(Gp1, "/global/D1/projects/umod/dipp/playground/result_graph/contacts.gexf")
nx.write_graphml(Gp1, "/global/D1/projects/umod/dipp/playground/result_graph/contacts.graphml")
print("Finished Drawing the First Graph")

#nx.write_gpickle(Gp2, "/home/dipp/Github/Graphs/test1.gpickle")
nx.write_gexf(Gp1, "/global/D1/projects/umod/dipp/playground/result_graph/mentions.gexf")
nx.write_graphml(Gp2, "/global/D1/projects/umod/dipp/playground/result_graph/mentions.graphml")

print("Finished drawing the second graph")



"""

print("..................DONE........................")

"""
df = pd.read_csv("/global/D1/projects/umod/dipp/playground/edge_list.csv")
df1 = df[['i', 'j']]
G = nx.Graph()
G = nx.from_pandas_edgelist(df1, 'i', 'j')

nx.draw_shell(G, with_labels=True) 
plt.savefig('/home/dipp/Results/test_net.pdf')

"""
