
CREATE TABLE workstation_activation (
                                        id VARCHAR(100) NOT NULL,
                                        warehouse_id VARCHAR(20) NOT NULL,
                                        workstation_id VARCHAR(35) NOT NULL,
                                        user_id BIGINT NOT NULL,
                                        process_group VARCHAR(30) NOT NULL,
                                        process_name VARCHAR(30) DEFAULT NULL,
                                        status VARCHAR(30) DEFAULT NULL,
                                        robotics_bind_code VARCHAR(100) DEFAULT NULL,
                                        created_at DATETIME NOT NULL,
                                        updated_at DATETIME NOT NULL,
                                        PRIMARY KEY (id)
);

CREATE TABLE robotics_request (
                                  id VARCHAR(100) NOT NULL,
                                  request_activation_id VARCHAR(100) NOT NULL,
                                  execution_activation_id VARCHAR(100) NOT NULL,
                                  workstation_activation_id VARCHAR(100) NOT NULL,
                                  rack VARCHAR(30) NOT NULL,
                                  face VARCHAR(10) DEFAULT NULL,
                                  status VARCHAR(30) DEFAULT NULL,
                                  process_name VARCHAR(100) DEFAULT NULL,
                                  nbt DATETIME NOT NULL,
                                  external_id VARCHAR(100) DEFAULT NULL,
                                  task_manager_status VARCHAR(100) DEFAULT NULL,
                                  robotics_task_id VARCHAR(200) DEFAULT NULL,
                                  client_priority INT(10) DEFAULT NULL,
                                  created_at DATETIME NOT NULL,
                                  updated_at DATETIME NOT NULL,
                                  PRIMARY KEY (id)
);


CREATE TABLE robotics_request_task (
                                       id VARCHAR(100) NOT NULL,
                                       robotics_request_id VARCHAR(100) NOT NULL,
                                       task_id BIGINT NOT NULL,
                                       status VARCHAR(30) DEFAULT NULL,
                                       PRIMARY KEY (id)
);

