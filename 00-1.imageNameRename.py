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

#This script is to remove unnecessary parts of the file name, to leave the Bioresource code and condition, 
#e.g. 2023.03.03-SR149206_07_CD_CD20.ndpi > SR149206_CD
#To edit the project.qpproj JSON file in order to edit the Sample IDs.
#The purpose is to unify the IDs for each project so that the annotations from QuPath can be copied over easily.

import json
import re

#Open the project.qpproj file and load into memory
with open('project.qpproj') as f:
    data = json.load(f)

#Find imageName object and remove the date, the slide ID number, the type of staining and the file format
for item in data['images']:

    s = item['imageName']
    #The regex:
    #( [ ^ - ] + ):
    #	() - group of 
    #	[] - specific set of characters
    #	^ - everything except for whatever is in the square brackets - needs to be at the begginning.
    #	- - except for this sign
    #	+ repeat for one or more occurrences
    #re.sub(pattern, replacement, input string, ...) - so the pattern of the s input was changed into the regex and the replacement is to remove everything except for the group 2 and 4 of characters.
    item['imageName'] = re.sub(r'([^-]+)-([^_]+)_([^_]+)_([^_]+)_([^.]+).ndpi', '\\2_\\5', s)
        
with open('project.qpproj', 'w') as outfile:
    json.dump(data, outfile, indent=4, ensure_ascii=False)
