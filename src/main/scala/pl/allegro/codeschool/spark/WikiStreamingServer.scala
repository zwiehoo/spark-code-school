package pl.allegro.codeschool.spark

import io.socket.SocketIOException
import pl.patrykpacewicz.wikimedia.stream.WikiMediaStream
import pl.patrykpacewicz.wikimedia.stream.listener.{EmptyListener, Listener}

class WikiListener extends Listener[AnyRef] {
    println("WikiListener")
    override def call(t: AnyRef): Unit = {
        println(t)
    }
}
class ConnectListener extends EmptyListener {
    println("ConnectListener")
    override def call(): Unit = {
        println("Connected yey")
    }
}

object ErrorListener extends Listener[SocketIOException] {
    override def call(t: SocketIOException): Unit = {
        println(t)
    }
}

object WikiStreamingServer extends App {
    val wiki = WikiMediaStream.builder()
        .mikimediaStreamUrl("http://stream.wikimedia.org/rc")
        .subscribeChannel("*.wikipedia.org")
        .onConnect(new ConnectListener)
        .onError(ErrorListener)
        .onChange(new WikiListener)
        .onDisconnect(new ConnectListener)
        .build()

    wiki.connect()
}
