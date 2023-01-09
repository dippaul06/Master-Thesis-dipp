import matplotlib.pyplot as plt
import pandas as pd

data_url = "/global/D1/projects/umod/dipp/playground/result_csv/urls.csv"
data_text = "/global/D1/projects/umod/dipp/playground/result_csv/texts.csv"
data_symbols = "/global/D1/projects/umod/dipp/playground/result_csv/symbols.csv"


url = pd.read_csv(data_url)
for_plot_df_1 = pd.DataFrame(url)
df = for_plot_df_1.head(50)
X = list(df.iloc[:, 0])
Y = list(df.iloc[:, 1])
plt.bar(X, Y)
plt.xlabel("URL")
plt.ylabel("Number")
#plt.yscale('symlog')
plt.xticks(rotation='90')
plt.savefig('/home/dipp/Results/URL_bar.pdf',bbox_inches='tight', dpi=150)
#plt.show()

for_plot_df_2 = pd.DataFrame(url)
df = for_plot_df_2.head(100)
X = list(df.iloc[:, 0])
Y = list(df.iloc[:, 1])
plt.plot(X, Y)
plt.xlabel("URL")
plt.ylabel("Number")
#plt.yscale('symlog')
plt.xticks(rotation='90')
plt.savefig('/home/dipp/Results/URL_line.pdf',bbox_inches='tight', dpi=150)
#plt.show()


text = pd.read_csv(data_text)
for_plot_df_3 = pd.DataFrame(text)
df = for_plot_df_3.head(50)
Y = list(df.iloc[:, 0])
X = list(df.iloc[:, 1])
plt.bar(X, Y)
plt.xlabel("Text")
plt.ylabel("Number")
plt.xticks(rotation='90')
plt.savefig('/home/dipp/Results/text_bar.pdf',bbox_inches='tight', dpi=150)


for_plot_df_4 = pd.DataFrame(text)
df = for_plot_df_4.head(100)
Y = list(df.iloc[:, 0])
X = list(df.iloc[:, 1])
plt.plot(X, Y)
plt.xlabel("Text")
plt.ylabel("Number")
plt.xticks(rotation='90')
plt.savefig('/home/dipp/Results/text_line.pdf',bbox_inches='tight', dpi=150)


symbols = pd.read_csv(data_symbols)
for_plot_df_5 = pd.DataFrame(symbols)
df = for_plot_df_5.head(50)
X = list(df.iloc[:, 0])
Y = list(df.iloc[:, 1])
plt.bar(X, Y)
plt.xlabel("Symbols")
plt.ylabel("Number")
plt.xticks(rotation='90')
plt.savefig('/home/dipp/Results/Symbols_bar.pdf',bbox_inches='tight', dpi=150)


for_plot_df_6 = pd.DataFrame(symbols)
df = for_plot_df_6.head(100)
X = list(df.iloc[:, 0])
Y = list(df.iloc[:, 1])
plt.plot(X, Y)
plt.xlabel("Symbols")
plt.ylabel("Number")
plt.xticks(rotation='90')
plt.savefig('/home/dipp/Results/Symbols_line.pdf',bbox_inches='tight', dpi=150)


