// (C) University of Edinburgh, Edinburgh, UK
// (C) Heriot-Watt University, Edinburgh, UK
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// This program is distributed in the hope that it will be
// useful but WITHOUT ANY WARRANTY; without even the implied
// warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
// PURPOSE.  See the GNU General Public License for more
// details.
///You should have received a copy of the GNU General Public
// License along with this program; if not, write to the Free
// Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
// Boston, MA  02110-1301, USA.

// The code reads the files within the project directory and exports the annotations as .txt. Other options include .geojson
def path = buildFilePath(PROJECT_BASE_DIR, 'annotations-' + getProjectEntry().getImageName() + '.txt')
def annotations = getAnnotationObjects()
new File(path).withObjectOutputStream {
    it.writeObject(annotations)
}
print 'Done!'