<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Cars */

$this->title = 'Create Vehicle';
?>
<div class="cars-create">
    <div class="cars-content">
        <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', ['index'], ['class' => 'btn btn-primary']) ?>
        </p>
        <?= $this->render('_form', [
            'model' => $model,
        ]) ?>
    </div>
</div>
