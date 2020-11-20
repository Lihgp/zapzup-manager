FROM openjdk:11 as zapzup

ENV API zapzup-manager.jar

RUN mkdir -p /app/

COPY target/${API} /app/${API}

RUN curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.9.2-amd64.deb
RUN dpkg -i filebeat-7.9.2-amd64.deb
RUN rm filebeat-7.9.2-amd64.deb

COPY ./filebeat.yml /etc/filebeat/filebeat.yml
RUN chmod go-w /etc/filebeat/filebeat.yml

RUN update-rc.d filebeat defaults
RUN update-rc.d filebeat enable