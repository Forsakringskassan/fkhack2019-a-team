<template>
    <div>
        <div class="enhetstabell">

        <SamlingsTabell @changeMonth="changeMonth"></SamlingsTabell>
        </div>
    </div>
</template>

<script>
    import SamlingsTabell from "./SamlingsTabell";

    export default {
        name: 'EnhetsVy',
        components: {
            SamlingsTabell
        },
        methods: {
            changeMonth: function(dir)
            {
                console.log(dir);
            }
        }
    }

</script>