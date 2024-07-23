# (C) University of Edinburgh, Edinburgh, UK
# (C) Heriot-Watt University, Edinburgh, UK
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be
# useful but WITHOUT ANY WARRANTY; without even the implied
# warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
# PURPOSE.  See the GNU General Public License for more
# details.
#
# You should have received a copy of the GNU General Public
# License along with this program; if not, write to the Free
# Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
# Boston, MA  02110-1301, USA.


import csv
import glob
import os
import sys
import pandas as pd


#Assign path where all the .csv files are located
path = sys.path[0]

#Open all the *.csv files
all_files = glob.glob(os.path.join(path,"*.csv"))

#Headers - a variable to change column headers in the final file
headers = ['Image', 'Region', 'Detections', 'Positive_cells', 'Positive_perc', 'Cells_per_area']

#Open all the csv files in the folder but only read columns 0, 2, 8, 10, 11 and 12 (Image, Region name, Total number of detections, Number of positive cells, percentage positive cells and area). Assign the header names based on the headers variable.
#Combine them into one dataFrame
#2 separators were used: tab (\t) and comma (,) because if the name of the file doesn't match the format "2020.01.01-SR000101_02_CD3_Healthy.ndpi" and was edited manually, usually the software changes the separations into commas.
data = (pd.read_csv(f, sep='\t|,', header=0, usecols=[0,2,8,10,11,12], names = headers, engine='python') for f in all_files)
combined_data = pd.concat(data)

#Write the new .csv file without indexing - it looks weird with index=True
combined_data.to_csv("Results_positive_cell.csv", index=False)
