import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { auth, Client, mapping } from 'cassandra-driver';
import { config } from 'dotenv';
config();

@Injectable()
export class CassandraService {
  private client: Client;
  mapper: mapping.Mapper;

  constructor(private configService: ConfigService) {
    this.createClient();
  }

  private createClient() {
    this.client = new Client({
      contactPoints: ['localhost'],
      localDataCenter: 'datacenter1',
      keyspace: 'distcomp',
      authProvider: new auth.PlainTextAuthProvider('cassandra', 'cassandra'),
      protocolOptions: {
        port: 9042,
      },
    });
  }

  createMapper(mappingOptions: mapping.MappingOptions) {
    if (this.client == undefined) {
      this.createClient();
    }
    return new mapping.Mapper(this.client, mappingOptions);
  }
}
