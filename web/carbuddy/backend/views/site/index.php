<?php

/* @var $this yii\web\View */

/* @var $usertypes backend\models\Users */
/* @var $charts backend\models\Charts */

$this->title = 'CarBuddy';

?>
<div class="site-index">
    <div class="body-content" style="padding-top: 10vh;">
        <div class="row" style="display: flex; justify-content: center;">
            <!--Adiciona gráficos automáticamente mediante o array charts do controller-->
            <?php foreach ($charts as $chart) { ?>
                <div class="col-md-3">
                    <h3><?= $chart->getLabel() ?></h3>
                    <?= $chart ?>
                </div>
            <?php } ?>
        </div>
    </div>

    <div class="body-content" style="margin-top: 5rem; margin-bottom: 2rem">

        <?php
        echo $this->render('about');
        $this->title = 'CarBuddy';
        ?>

    </div>
</div>
