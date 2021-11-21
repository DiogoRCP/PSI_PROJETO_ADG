<?php

use yii\helpers\Html;
use yii\grid\GridView;
use yii\helpers\Url;
use yii\helpers\VarDumper;

/* @var $this yii\web\View */
/* @var $model frontend\models\Cars */
$this->title = 'Vehicles';
?>
<div class="cars-index">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= ($create == true)?Html::a('Create Vehicle', ['create'], ['class' => 'btn btn-secondary']):"" ?>
    </p>

    <div class="body-content">
        <div class="row">
            <?php foreach ($model as $car) { ?>
                <div class="col-md-3">
                    <a href="<?= Url::to(['car/view', 'id' => $car->id]) ?>">
                        <h4><?= $car->brand ?> <?= $car->model ?></h4>
                        <?= $car->veicleImage(200) ?>
                        <h5><?= $car->registration ?></h5>
                    </a>
                </div>
            <?php } ?>
        </div>
    </div>
</div>
