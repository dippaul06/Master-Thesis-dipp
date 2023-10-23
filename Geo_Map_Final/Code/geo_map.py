import pandas as pd
import time
from geopy.geocoders import Nominatim 
import folium

def geocode_country(country_name):
    geolocator = Nominatim(user_agent="your_app_name")
    location = geolocator.geocode(country_name)
    if location:
        return location.latitude, location.longitude
    else:
        return None, None

def create_map(data):
    world_map= folium.Map(tiles="cartodbpositron", zoom_start=10)
    m = folium.Map(tiles='cartodbpositron', zoom_start=20)

    #marker_cluster = MarkerCluster().add_to(m)

    for index,row in data.iterrows():
        count = row['Connections']
        lat = row['Latitude']
        lon = row['Longitude']
        radius = 30
        popup_text = count



        #folium.CircleMarker(location = [lat, lon], radius=radius, tooltip=count, color="cornflowerblue", stroke=False, fill=True, fill_opacity=0.6, opacity=1).add_to(m)
        if(count >= 1000000):
            folium.map.Marker( location = [lat, lon], icon=folium.DivIcon(icon_size=(150,36),icon_anchor=(0,0),html='<div style="font-size: 20pt", font-weight: 900>%s</div>' %count), popup= popup_text).add_to(m)
        if(count < 1000000 and count >100000):
            folium.map.Marker( location = [lat, lon], icon=folium.DivIcon(icon_size=(150,36),icon_anchor=(0,0),html='<div style="font-size: 15pt", font-weight: 900>%s</div>' %count), popup= popup_text).add_to(m)
        if(count < 100000):
            folium.map.Marker( location = [lat, lon], icon=folium.DivIcon(icon_size=(150,36),icon_anchor=(0,0),html='<div style="font-size: 10pt", font-weight: 900>%s</div>' %count), popup= popup_text).add_to(m)

    return m


def data_format(df):
    df['Connections'] = df['Connections'].astype(int)
    df = df.drop(['Population'], axis=1)
    for index, row in df.iterrows():
        row['Latitude'], row['Longitude'] = geocode_country(row['Country Name'])
    index_names = df[ (df['Connections'] < 1000)].index  
    df.drop(index_names, inplace = True)
    df.reset_index(inplace = True) 
    df.at[56, 'Latitude'] = 31.50000000
    df.at[56, 'Longitude'] = 34.75000000
    df = df.drop(['index'], axis=1)
    return df


def __main__():
    csv_file = '/home/dipp/Github/Master-Thesis-dipp/Geo_Map_Final/Data/Country_Data_V2_Latest.csv'
    data = pd.read_csv(csv_file)
    data = data_format(data)
    world_map = create_map(data)
    #world_map.save('/home/dipp/Github/Master-Thesis-dipp/Geo_Map_Final/Result/world_map_final.html')
    world_map.save('/home/dipp/Github/Master-Thesis-dipp/Geo_Map_Final/Result/world_map_final_new.html')
    


if __name__=="__main__":
    start_time = time.time()
    __main__()
    print("--- %s seconds ---" % (time.time() - start_time)) 