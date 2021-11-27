<?php

use yii\helpers\Html;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $model frontend\models\Cars */
/* @var $create */
$this->title = 'Garage';
?>
<div class="cars-index">
    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= ($create)?Html::a('Add Vehicle', ['create'], ['class' => 'btn btn-secondary']):"" ?>
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
