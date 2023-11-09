create table if not exists matches_seq
(
    next_val bigint null
);

create table if not exists team
(
    id      bigint       not null,
    country varchar(255) not null,
    name    varchar(255) not null,
    stadium varchar(255) not null,
    primary key (id)
    );

create table if not exists team_seq
(
    next_val bigint null
);

create table if not exists tournament
(
    id     bigint       not null,
    name   varchar(255) not null,
    prize  int          not null,
    status tinyint      not null,
    primary key (id),
    check (`status` between 0 and 2)
    );

create table if not exists matches
(
    id            bigint       not null,
    match_result  tinyint      not null,
    number        int          not null,
    stadium       varchar(255) not null,
    team1score    int          not null,
    team2score    int          not null,
    team1_id      bigint       not null,
    team2_id      bigint       not null,
    tournament_id bigint       not null,
    match_status  tinyint      not null,
    primary key (id),
    constraint FK3nm68dgw6bufmsy61v3h21wx7
    foreign key (team1_id) references team (id),
    constraint FK6u6jn45m2juuk50mg6hxn71p7
    foreign key (tournament_id) references tournament (id),
    constraint FK8bwbvhi5hdulqxl1m3c3pt120
    foreign key (team2_id) references team (id),
    check (`match_result` between 0 and 2),
    check (`match_status` between 0 and 2)
    );

create table if not exists tournament_seq
(
    next_val bigint null
);

create table if not exists tournament_team
(
    tournament_id bigint not null,
    team_id       bigint not null,
    constraint UKb2o9owx7jm66polh9kayeaayo
    unique (tournament_id, team_id),
    constraint FKe2nr7bhms79ni7aa6jjdf3ioj
    foreign key (team_id) references team (id),
    constraint FKnpt2r8h2uwq1j0iesu13so87p
    foreign key (tournament_id) references tournament (id)
    );

create table if not exists tournament_team_result
(
    id             bigint not null,
    draws          int    not null,
    goals_against  int    not null,
    goals_for      int    not null,
    losses         int    not null,
    matches_played int    not null,
    points         int    not null,
    wins           int    not null,
    team_id        bigint not null,
    tournament_id  bigint not null,
    primary key (id),
    constraint FK8quxixyxowxda8nr5svvbuolp
    foreign key (team_id) references team (id),
    constraint FKbk3pfbbm1gd6gclonulm6yjp
    foreign key (tournament_id) references tournament (id)
    );

create table if not exists tournament_team_result_seq
(
    next_val bigint null
);

