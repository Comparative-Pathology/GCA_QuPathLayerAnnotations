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

// Set image settings. NOTE: each staining needs adjusting of these values.
setImageType('BRIGHTFIELD_H_DAB');
setColorDeconvolutionStains('{"Name" : "H-DAB estimated", "Stain 1" : "Hematoxylin", "Values 1" : "0.78105 0.56189 0.27247", "Stain 2" : "DAB", "Values 2" : "0.49354 0.56186 0.66387", "Background" : " 223 222 230"}');
selectAnnotations();
// Generate additional annotation based on mucosa pixel classifier
createAnnotationsFromPixelClassifier("Mucosa_tissue_IHC", 0.0, 0.0)