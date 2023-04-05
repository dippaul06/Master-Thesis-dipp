import csv
from geopy.geocoders import Nominatim
from time import sleep

import folium
from folium.plugins import MarkerCluster
from folium import IFrame


data_file = '/home/dipp/Github/Master-Thesis-dipp/GEO_MAP/sorted_country_code_with_count_new.csv'

## Here we are keeping the Country Code, Count, retirved Latitude and Lonitude value
same_country_dict = {}

with open(data_file, newline='', encoding='utf-8') as fd:
    text_data = csv.DictReader(fd)
    count = 0
    for line in text_data:
        if((line['i']==line['j']) and (line['i']!= 'None') and (line['j']!='None')):
            count += 1
            loc_code = line['i'].replace("'",'')
            #print(count,' ',loc_code)
            try:
                loc = Nominatim(user_agent='GetLoc')
                getloc = loc.geocode(loc_code)
                lat = getloc.latitude
                lon = getloc.longitude
                same_country_dict[loc_code] = [line['count'], lat, lon]
                sleep(1)
            except Exception:
                print(loc_code)
                continue

##Crates an empty Map
world_map= folium.Map(tiles="cartodbpositron", zoom_start=10)


#icon=folium.DivIcon(html=f"""<div style="font-family: courier new; color: {'red'}">{"{:.0f}".format(int(b[0]))}</div>""")
for a,b in same_country_dict.items():
    lat = b[1]
    long = b[2]
    count = int(b[0])
    radius = 5
    popup_text = count
    radius = 30
    folium.CircleMarker(
    location=[lat, long],
    radius=radius,
    color="cornflowerblue",
    stroke=False,
    fill=True,
    fill_opacity=0.6,
    opacity=1,
    #popup="{} pixels".format(radius),
    popup=popup_text,
    tooltip=count,
    ).add_to(world_map)
    radius = 5
    folium.Marker(location = [lat, long],icon=folium.DivIcon(html=f"""<div style="color: {'green'};">{count}</div>"""),popup= popup_text).add_to(world_map)

    world_map.save('first_map_test.html')