package search

import kotlin.coroutines.coroutineContext

data class NewDB (val size: Int) {
    private val list = MutableList<String>(size) { "" }
    var ind = 0
    var mapa = mutableMapOf<String, MutableList<Int>?>()

    enum class Search_strategy(val text: String){
        ANY("ANY"),
        ALL("ALL"),
        NONE("NONE")
    }





    fun add(s:String, lineNum: Int){
        val words = s.split(" ")
        if(ind>=size) throw Exception("List is FULL!")
        list[ind]=s
        ind++
        for(word in words){
                if(mapa.containsKey(word.lowercase())){
                    val tmp = mapa[word.lowercase()]
                    tmp?.add(lineNum)
                    mapa.replace(word.lowercase(),tmp)
                } else{
                    mapa.put(word.lowercase(),mutableListOf(lineNum))
                }
        }
    }

    fun searchFor(s:String, searchStrtegy: Search_strategy): MutableList<String>{
        /*val out = MutableList<String>(0){""}
        val tmp = mapa.get(s.lowercase())
        if(tmp!= null)
            for(ind in tmp)
                out+=list[ind]
        return out*/
        when (searchStrtegy){
            Search_strategy.ALL-> return searchForAll(s)
            Search_strategy.ANY-> return searchForAny(s)
            Search_strategy.NONE-> return searchForNone(s)
        }
    }

    private fun searchForAny(s:String):MutableList<String>{
        val out = MutableList<String>(0){""}
        val indSet = mutableSetOf<Int>()
        val words = s.split(" ")

        for(word in words){
            val tmp = mapa.get(word.lowercase())

            if (tmp != null) {
                indSet.addAll(tmp)

            }
        }
        for(ind in indSet)
            out+=list[ind]
        return out
    }
    private fun searchForAll(s:String):MutableList<String>{
        val out = MutableList<String>(0){""}
        var indSet = mutableSetOf<Int>()
        val words = s.split(" ")

        for(word in words){
            val tmp = mapa.get(word.lowercase())
            val tmp2=indSet
            if (tmp != null) {
                indSet=tmp.intersect(tmp2).toMutableSet()
            }
        }
        for(ind in indSet)
            out+=list[ind]
        return out
    }
    private fun searchForNone(s:String):MutableList<String>{
        val out = MutableList<String>(0){""}
        var indSet = mutableSetOf<Int>()
        val words = s.split(" ")

        for(word in words){
            val tmp = mapa.get(word.lowercase())


            if (tmp != null) {
                indSet.addAll(tmp)

            }
        }
        for(ind in 0..size-1) if(!indSet.contains(ind))
            out+=list[ind]
        return out
    }

    fun printAll() = println(list.joinToString("\n"))
    fun printMap() = println(mapa.toString())

}