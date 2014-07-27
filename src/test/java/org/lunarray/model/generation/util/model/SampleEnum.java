/* 
 * Model Tools.
 * Copyright (C) 2013 Pal Hargitai (pal@lunarray.org)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lunarray.model.generation.util.model;

import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.util.BooleanInherit;

public enum SampleEnum {

	VALUE_01("some value 01"), VALUE_02("Some other value.");

	@PresentationHint(name = BooleanInherit.TRUE)
	private final String label;

	private SampleEnum(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
