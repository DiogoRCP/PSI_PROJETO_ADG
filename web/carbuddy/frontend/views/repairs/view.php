<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model frontend\models\Repairs */

$this->title = $model->car->brand . " " . $model->car->model;
//$this->params['breadcrumbs'][] = ['label' => 'Repairs', 'url' => ['index']];
//$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="repairs-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-light']) ?>
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-secondary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-dark',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'id',
            ['label' => 'Car',
                'value' => $model->car->brand . " " . $model->car->model],
            'kilometers',
            'repairtype',
            'repairdescription',
            'state',
            ['label' => 'Contributor',
                'value' => $model->contributor->user->username],
            'repairdate',
        ],
    ]) ?>

</div>
