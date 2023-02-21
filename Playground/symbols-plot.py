import pandas as pd
import matplotlib.pyplot as plot

df = pd.read_csv("/global/D1/projects/umod/dipp/playground/result_csv/symbols.csv")

for_plot_df = df.head(10)

fig = for_plot_df.plot.bar(x="text", y="num", rot=1, title="Test", logy=True)
fig.figure.savefig('/home/dipp/Github/Playground/DEMO_RES/symbol_bar.pdf')

#plot.show(block=True)