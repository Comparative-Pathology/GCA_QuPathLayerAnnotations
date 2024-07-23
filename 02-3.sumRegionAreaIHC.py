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

# To calculate the sum of each section area based on the annotations

import csv
import os
import sys

# Function to sum values in column 15 of a CSV file and store corresponding IDs
def sum_column_15(csv_file):
    id_value_map = {}  # To store ID-value mapping
    with open(csv_file, 'r') as file:
        reader = csv.reader(file, delimiter='\t')  # Using tab as delimiter
        for row in reader:
            try:
                id_value_map[row[0]] = id_value_map.get(row[0], 0) + float(row[13])  # Assuming column 1 is at index 0 and column 15 is at index 14
            except (ValueError, IndexError):
                pass  # Ignore non-numeric values or index errors
    return id_value_map

# Path to the directory containing CSV files
directory = sys.path[0]

# Dictionary to store ID-value sums
total_sum_dict = {}

# Iterate through each file in the directory
for filename in os.listdir(directory):
    if filename.endswith(".csv"):
        file_path = os.path.join(directory, filename)
        id_value_map = sum_column_15(file_path)
        # Update total_sum_dict with values from current file
        for id_, value in id_value_map.items():
            total_sum_dict[id_] = total_sum_dict.get(id_, 0) + value

# Write the total sums to results.csv
with open('results.csv', 'w', newline='') as result_file:
    writer = csv.writer(result_file)
    writer.writerow(['ID', 'Result of Sum'])
    for id_, total_sum in total_sum_dict.items():
        writer.writerow([id_, total_sum])

print("Results written to results.csv")
