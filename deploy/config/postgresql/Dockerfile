FROM postgres:latest

# Set correct Timezone container
RUN apt-get upgrade \
	&& apt-get install -y tzdata

ENV TZ=Europe/Rome
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Remove not necessary files
RUN rm -rf /var/lib/apt/lists/*