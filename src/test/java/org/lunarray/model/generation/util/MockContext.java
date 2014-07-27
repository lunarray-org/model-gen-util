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
package org.lunarray.model.generation.util;

import java.util.Deque;
import java.util.LinkedList;

import org.lunarray.model.descriptor.model.property.PropertyDescriptor;

/**
 * A mock context.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class MockContext
		implements Context {

	/** A stack. */
	private final Deque<PropertyDescriptor<?, ?>> stack;

	/**
	 * Default constructor.
	 */
	public MockContext() {
		this.stack = new LinkedList<PropertyDescriptor<?, ?>>();
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDescriptor<?, ?> popPrefix() {
		return this.stack.pop();
	}

	/** {@inheritDoc} */
	@Override
	public void pushPrefix(final PropertyDescriptor<?, ?> prefix) {
		this.stack.push(prefix);
	}
}
