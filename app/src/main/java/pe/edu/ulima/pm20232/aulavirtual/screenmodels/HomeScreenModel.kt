package pe.edu.ulima.pm20232.aulavirtual.screenmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pe.edu.ulima.pm20232.aulavirtual.models.BodyPart
import pe.edu.ulima.pm20232.aulavirtual.models.Exercise
import pe.edu.ulima.pm20232.aulavirtual.models.Pokemon
import pe.edu.ulima.pm20232.aulavirtual.models.User
import pe.edu.ulima.pm20232.aulavirtual.services.BodyPartService
import pe.edu.ulima.pm20232.aulavirtual.services.ExerciseMemberService
import pe.edu.ulima.pm20232.aulavirtual.services.ExerciseService
import pe.edu.ulima.pm20232.aulavirtual.services.PokemonService
import pe.edu.ulima.pm20232.aulavirtual.services.UserService

class HomeScreenViewModel: ViewModel(){
    //var pokemons = mutableStateListOf<Pokemon>()
    val generationsMap = mutableMapOf<Int, String>()

    private var _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> get() = _pokemons
    fun setPokemons(newItems: List<Pokemon>) {
        _pokemons.value = newItems
    }

    fun listAll(){
        //val pokemonService: PokemonService = PokemonService()
        //val list = pokemonService.listAll()
        //setPokemons(list)
    }

    fun getGenerations(){
        for(p: Pokemon in _pokemons.value){
            val generationId = p.generationId
            val generationName = p.generationName
            if(!generationsMap.containsKey(generationId)){
                generationsMap[generationId] = generationName
            }
        }
    }

    /*
    fun filterByGenerations(generationId: Int){
        val pokemonService: PokemonService = PokemonService()
        val list = pokemonService.pokemonListByGenerationId(generationId)
        setPokemons(list)
    }
    */
    // TRABAJO

    val bodyPartsMap = mutableMapOf<Int, String>()

    fun getUserBodyParts(username: String){
        val serviceUser : UserService = UserService()
        val serviceExcercise: ExerciseService = ExerciseService()
        val serviceExerciseMember : ExerciseMemberService = ExerciseMemberService()
        val userId = serviceUser.getUserId(username)
        val listExerciseId = serviceExerciseMember.listUserExcerciseId(userId)
        val bodyPartService: BodyPartService = BodyPartService()
        var bodyPartList: ArrayList<BodyPart> = bodyPartService.bodyPartList
        var bodyPartFilterList: List<Int> = serviceExcercise.listBodyPartsIdByUser(listExerciseId)
        for(p: BodyPart in bodyPartList){
            val id = p.id
            val name = p.name
            for (filterId in bodyPartFilterList ){
                if (id == filterId ){
                 if(!bodyPartsMap.containsKey(id)){
                    bodyPartsMap[id] = name
            }
            }
            }
        }
    }

    private var _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> get() = _exercises
    fun setExercises(newItems: List<Exercise>) {
        _exercises.value = newItems
    }

    fun listUserExercises(username: String){
        val serviceUser : UserService = UserService()
        val serviceExcercise: ExerciseService = ExerciseService()
        val serviceExerciseMember : ExerciseMemberService = ExerciseMemberService()
        val userId = serviceUser.getUserId(username)
        val listExerciseId = serviceExerciseMember.listUserExcerciseId(userId)
        val listExercise = serviceExcercise.listUserExercise(listExerciseId)
        setExercises(listExercise)
    }

    fun filterByBodyPartsByUser(bodyPartId: Int, exerciseList: List<Exercise>){
        val service: ExerciseService = ExerciseService()
        val list = service.exerciseListByBodyPartIdByUser(bodyPartId, exerciseList)
    }

    fun filterByBodyParts(bodyPartId: Int){
        val service: ExerciseService = ExerciseService()
        val list = service.exerciseListByBodyPartId(bodyPartId)
        setExercises(list)
    }
}
//USAR PARA LA PANTALLA DE EJERCICIOS COMPLETOS
//package pe.edu.ulima.pm20232.aulavirtual.screenmodels
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import pe.edu.ulima.pm20232.aulavirtual.models.BodyPart
//import pe.edu.ulima.pm20232.aulavirtual.models.Exercise
//import pe.edu.ulima.pm20232.aulavirtual.models.Pokemon
//import pe.edu.ulima.pm20232.aulavirtual.services.BodyPartService
//import pe.edu.ulima.pm20232.aulavirtual.services.ExerciseService
//import pe.edu.ulima.pm20232.aulavirtual.services.PokemonService
//
//class HomeScreenViewModel: ViewModel(){
//    //var pokemons = mutableStateListOf<Pokemon>()
//    val generationsMap = mutableMapOf<Int, String>()
//
//    private var _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
//    val pokemons: StateFlow<List<Pokemon>> get() = _pokemons
//    fun setPokemons(newItems: List<Pokemon>) {
//        _pokemons.value = newItems
//    }
//
//    fun listAll(){
//        //val pokemonService: PokemonService = PokemonService()
//        //val list = pokemonService.listAll()
//        //setPokemons(list)
//    }
//
//    fun getGenerations(){
//        for(p: Pokemon in _pokemons.value){
//            val generationId = p.generationId
//            val generationName = p.generationName
//            if(!generationsMap.containsKey(generationId)){
//                generationsMap[generationId] = generationName
//            }
//        }
//    }
//
//    /*
//    fun filterByGenerations(generationId: Int){
//        val pokemonService: PokemonService = PokemonService()
//        val list = pokemonService.pokemonListByGenerationId(generationId)
//        setPokemons(list)
//    }
//    */
//    // TRABAJO
//
//    val bodyPartsMap = mutableMapOf<Int, String>()
//
//    fun getBodyParts(){
//        val bodyPartService: BodyPartService = BodyPartService()
//        var bodyPartList: ArrayList<BodyPart> = bodyPartService.bodyPartList
//        for(p: BodyPart in bodyPartList){
//            val id = p.id
//            val name = p.name
//            if(!bodyPartsMap.containsKey(id)){
//                bodyPartsMap[id] = name
//            }
//        }
//    }
//
//    private var _exercises = MutableStateFlow<List<Exercise>>(emptyList())
//    val exercises: StateFlow<List<Exercise>> get() = _exercises
//    fun setExercises(newItems: List<Exercise>) {
//        _exercises.value = newItems
//    }
//
//    fun listAllExercises(){
//        val service: ExerciseService = ExerciseService()
//        val list = service.listAll()
//        setExercises(list)
//    }
//
//    fun filterByBodyParts(bodyPartId: Int){
//        val service: ExerciseService = ExerciseService()
//        val list = service.exerciseListByBodyPartId(bodyPartId)
//        setExercises(list)
//    }
//}