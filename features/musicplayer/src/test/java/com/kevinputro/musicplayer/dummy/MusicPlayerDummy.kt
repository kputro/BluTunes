package com.kevinputro.musicplayer.dummy

import com.kevinputro.entity.response.SearchResponse
import com.kevinputro.entity.response.SongResponse

object MusicPlayerDummy {
  fun createSearchMusicResponse(): SearchResponse<SongResponse> =
    SearchResponse(
      resultCount = 2,
      results = createDummySongItems()
    )

  fun createDummySongItems(): List<SongResponse> =
    listOf(
      SongResponse(
        trackId = 1207120448,
        collectionId = 1207120422,
        artistId = 580391756,
        artistName = "The Chainsmokers & Coldplay",
        collectionName = "Memories...Do Not Open",
        trackName = "Something Just Like This",
        previewUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview126/v4/24/76/9a/24769a15-f4e4-0dd6-e69d-561265d003e1/mzaf_4429079767719665040.plus.aac.p.m4a",
        artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music125/v4/9d/56/6f/9d566f55-5253-bed6-5c31-df952dae649d/886446379289.jpg/100x100bb.jpg",
        releaseDate = "2017-02-22T08:00:00Z",
        primaryGenreName = "Dance"
      ),
      SongResponse(
        trackId = 829910927,
        collectionId = 829909653,
        artistId = 471744,
        artistName = "Coldplay",
        collectionName = "Ghost Stories",
        trackName = "A Sky Full of Stars",
        previewUrl = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview116/v4/a2/31/4b/a2314b97-10b6-190c-72b3-45cc21bbf56b/mzaf_740612971315603868.plus.aac.p.m4a",
        artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Features125/v4/60/90/ad/6090adc3-8863-861d-afcc-23c55c6fe5da/dj.vmtulfyu.jpg/100x100bb.jpg",
        releaseDate = "2014-05-02T07:00:00Z",
        primaryGenreName = "Alternative"
      ),
    )
}
