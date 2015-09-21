package com.appeals.result.model;

import javax.xml.bind.annotation.XmlEnumValue;


public enum AppealStatus {
    @XmlEnumValue(value="GENERATING")
    GENERATING,
    @XmlEnumValue(value="PROCESSING")
    PROCESSING, 
    @XmlEnumValue(value="READY")
    READY, 
    @XmlEnumValue(value="DONE")
    DONE
}
