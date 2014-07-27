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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.lunarray.model.descriptor.model.annotations.Key;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;

public class Sample02
		implements Serializable {

	public static final Sample02 SAMPLE_01;
	public static final Sample02 SAMPLE_02;
	public static final Sample02 SAMPLE_03;
	public static final List<Sample02> SAMPLES;
	/** Serial id. */
	private static final long serialVersionUID = -6807595966898520695L;

	@Key
	private String id;

	@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(visible = BooleanInherit.FALSE)) })
	private Integer othervalue = 2;

	@PresentationHint(name = BooleanInherit.TRUE)
	private String testValue;

	static {
		SAMPLE_01 = new Sample02();
		Sample02.SAMPLE_01.id = "id 1";
		Sample02.SAMPLE_01.testValue = "test 1";
		SAMPLE_02 = new Sample02();
		Sample02.SAMPLE_02.id = "id 2";
		Sample02.SAMPLE_02.testValue = "test 2";
		SAMPLE_03 = new Sample02();
		Sample02.SAMPLE_03.id = "id 3";
		Sample02.SAMPLE_03.testValue = "test 3";
		SAMPLES = new LinkedList<Sample02>();
		Sample02.SAMPLES.add(Sample02.SAMPLE_01);
		Sample02.SAMPLES.add(Sample02.SAMPLE_02);
		Sample02.SAMPLES.add(Sample02.SAMPLE_03);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public String getId() {
		return this.id;
	}

	public Integer getOthervalue() {
		return this.othervalue;
	}

	public String getTestValue() {
		return this.testValue;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setOthervalue(final Integer othervalue) {
		this.othervalue = othervalue;
	}

	public void setTestValue(final String testValue) {
		this.testValue = testValue;
	}
}
