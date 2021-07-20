package com.cseiu.passnetorganizer.domain.command;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseCommand {
    protected String aggregateId;
}
