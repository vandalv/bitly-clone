--changeset dauzhukv:2
CREATE TABLE IF NOT EXISTS bitly_db.url_mapping (
    link VARCHAR(255) NOT NULL,
    short_link VARCHAR(10) NOT NULL,
    CONSTRAINT pk_url_mapping PRIMARY KEY (link),
    CONSTRAINT uk_link UNIQUE (link),
    CONSTRAINT uk_short_link UNIQUE (short_link)
);

-- Create indexes for fast search
CREATE INDEX idx_link ON bitly_db.url_mapping (link);
CREATE INDEX idx_short_link ON bitly_db.url_mapping (short_link);

--rollback DROP TABLE bitly_db.url_mapping;