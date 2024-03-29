package sample.settings.gensagames.samplejetpackmvvm.model.net

import com.thedeanda.lorem.LoremIpsum
import sample.settings.gensagames.samplejetpackmvvm.model.dto.HeaderIntroObject
import sample.settings.gensagames.samplejetpackmvvm.model.dto.InfoObject
import javax.inject.Inject
import kotlin.random.Random


/**
 * Stub implementation of API Service, or Repository files.
 */
class SampleApi2 @Inject constructor(
    private val tag: String
) {

    fun getInfo(): String {
        return tag
    }

    fun getInfoObjects(): List<InfoObject> {
        val list = mutableListOf<InfoObject>()

        for (i in 1..Random(System.currentTimeMillis())
            .nextInt(15, 25)) {

            list.add(
                InfoObject(
                    LoremIpsum.getInstance().name,
                    LoremIpsum.getInstance().email,
                    LoremIpsum.getInstance().getWords(4, 12),
                    getRandomImageUrl(i),
                    Random(System.currentTimeMillis())
                        .nextBoolean()
                )
            )
        }
        return list
    }

    fun getHeaderIntroObject(): HeaderIntroObject {
        return HeaderIntroObject(
            LoremIpsum.getInstance().firstName + " " +
            LoremIpsum.getInstance().lastName,
            LoremIpsum.getInstance().email
        )
    }

    /**
     * The same link will return random image, each time,
     * See Picsum.com for more details.
     */
    private fun getRandomImageUrl(idx : Int): String {
        return "https://picsum.photos/id/${305+idx}/600/400"
    }
}