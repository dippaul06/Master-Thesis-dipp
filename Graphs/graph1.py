import pandas as pd
import networkit as nt
import networkx as nx
import csv

print(".........................Starting.......................")
#file_folder = "/global/D1/projects/umod/dipp/playground/edge_list.csv"
#num = sum(1 for line in open(file_folder))
#print("Total Lines:" , num)

print("Starting Dataframe")
df = pd.read_csv("/global/D1/projects/umod/dipp/playground/edge_list.csv")
print("Made the DataFrame")

print("Starting Graph")
Graph = nx.from_pandas_edgelist(df=df, source='i', target='j', edge_attr='contacts', create_using=nx.DiGraph)
print("MADE THE GRAPH - CONTACTS")


nodes = nx.number_of_nodes(Graph)
print("Number of Nodes = ", nodes)
edges = nx.number_of_edges(Graph)
print("Number of Edges = ", edges)

print("Starting Calculations............")
degree_list_all = Graph.degree()
print("Degree Calculations done............")
degree_list_in = Graph.in_degree()
print("Degree in Calculations done............")
degree_list_out = Graph.out_degree()
print("Degree out Calculations done............")

#local_cluster_conefficient = nx.clustering(Graph)

print("Calculations Done...................")


print("Converting everything to list............")
con_degree_list_all = [k for k in degree_list_all]
con_degree_list_in = [k for k in degree_list_in]
con_degree_list_out = [k for k in degree_list_out]
#con_local_cluster_conefficient = list(local_cluster_conefficient.items())
print("Convertion complete.....................")


print("Writing to Csv...............")
with open("/global/D1/projects/umod/dipp/playground/result_graph/degree_all.csv", 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerows(con_degree_list_all)

with open("/global/D1/projects/umod/dipp/playground/result_graph/degree_in.csv", 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerows(con_degree_list_in)

with open("/global/D1/projects/umod/dipp/playground/result_graph/degree_out.csv", 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerows(con_degree_list_out)

#with open("/global/D1/projects/umod/dipp/playground/result_graph/local_cluster.csv", 'w', newline='') as f:
#    writer = csv.writer(f)
#    writer.writerows(con_local_cluster_conefficient)
#print("Writing to CSV Done...................")


print("..........................Done...............................")