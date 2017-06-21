package io.cyberdolphin.biketracker

/**
 * Created by Mikolaj Wielocha on 21/06/17
 */

case class ClientConfig(bingKey: String)

case class AppConfig(client: ClientConfig, gpxDir: String)
