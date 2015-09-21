package com.appeals.result.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Comment {
    @XmlEnumValue(value="No Comments")
    NO_COMMENTS,
    @XmlEnumValue(value="TOTALLING ERROR")
    TOTALLING_ERROR,
    @XmlEnumValue(value="ANSWER IS CORRECT")
    ANSWER_IS_CORRECT
}
